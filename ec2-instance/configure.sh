#!/bin/bash

while ! [[ "$count" =~ ^[1-5]$ ]] ;do
    read -p サーバ台数を1~5で入力して下さい： count
done

IFS=
readarray data < "cdk.json"
content="${data[*]}" # 配列に読み込んだものを結合する

content=$(echo $content | sed -e "s/\"count\": \"[1-5]\"/\"count\": \"$count\"/g")

echo $content | tee cdk.json
