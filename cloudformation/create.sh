# Turning off the AWS pager so that the CLI doesn't open an editor for each command result
export AWS_PAGER=""

# --stack-name 스택 이름 지정
# --template-body 클라우드포메이션이 읽어야할 템플릿의 위치
# --capabilities 스택이 IAM을 변경할 수 있도록 설정
aws cloudformation create-stack \
  --stack-name stratospheric-basic-network \
  --template-body file://network.yml \
  --capabilities CAPABILITY_IAM

aws cloudformation wait stack-create-complete \
  --stack-name stratospheric-basic-network

# --parameters : 디폴트 설정을 사용하지 않길 바라는 파라미터들을 전달한다.
aws cloudformation create-stack \
  --stack-name stratospheric-basic-service \
  --template-body file://service.yml \
  --parameters \
      ParameterKey=NetworkStackName,ParameterValue=stratospheric-basic-network \
      ParameterKey=ServiceName,ParameterValue=stratospheric-todo-app \
      ParameterKey=ImageUrl,ParameterValue=docker.io/hiho0701/restapitest-v1:latest \
      ParameterKey=ContainerPort,ParameterValue=8080

aws cloudformation wait stack-create-complete --stack-name stratospheric-basic-service

CLUSTER_NAME=$(aws cloudformation describe-stacks --stack-name stratospheric-basic-network --output text --query 'Stacks[0].Outputs[?OutputKey==`ClusterName`].OutputValue | [0]')
echo "ECS Cluster:       " $CLUSTER_NAME

TASK_ARN=$(aws ecs list-tasks --cluster $CLUSTER_NAME --output text --query 'taskArns[0]')
echo "ECS Task:          " $TASK_ARN

ENI_ID=$(aws ecs describe-tasks --cluster $CLUSTER_NAME --tasks $TASK_ARN --output text --query 'tasks[0].attachments[0].details[?name==`networkInterfaceId`].value')
echo "Network Interface: " $ENI_ID

PUBLIC_IP=$(aws ec2 describe-network-interfaces --network-interface-ids $ENI_ID --output text --query 'NetworkInterfaces[0].Association.PublicIp')
echo "Public IP:         " $PUBLIC_IP

echo "You can access your service at http://$PUBLIC_IP:8080"
