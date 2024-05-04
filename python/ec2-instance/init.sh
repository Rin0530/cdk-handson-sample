#!/bin/bash
# Use this to install software packages
pip install -r requirements.txt

creator=$(aws iam get-user --query User.UserName --output text)

while ! [[ "$cidr" =~ ^[0-9]{1,3}$ ]] ;do
    read -p 学番下3桁を入力してください： cidr
done

IFS=
readarray data < "cdk.json"
content="${data[*]}" # 配列に読み込んだものを結合する
content=${content//cidrsample/$cidr}
content=${content//creatorsample/$creator}

echo $content | tee cdk.json
