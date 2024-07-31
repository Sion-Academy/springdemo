# 检查是否传入参数
if [ -z "$1" ]; then
    echo "请传入容器名称作为参数。"
    exit 1
fi
# 容器名称
CONTAINER_NAME=$1
# 打包
mvn clean
mvn package
# 构建镜像
docker build -t ices/storage_master:latest .
# 检查容器是否存在
if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
    # 停止并删除现有的容器
    docker stop $CONTAINER_NAME
    docker rm $CONTAINER_NAME
fi
# 创建并启动新容器
docker run -d -p 10100:8080 --name $CONTAINER_NAME -e MYSQL_HOST=175.27.150.229:13306 ices/storage_master:latest