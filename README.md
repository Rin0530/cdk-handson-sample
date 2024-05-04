# kithub ハンズオン用 cdk サンプルコード

EC2を1つ作成する単純なコードです。

## セットアップ
任意の言語のec2-instanceディレクトリに移動し、`./configure.sh`を実行してください。

## デプロイ
cdk.jsonのcidr、creator、cpuType、instanceSizeを埋めた後、`npx cdk deploy`でデプロイできます。
ただし、cidr、creator、cpuType、instanceSizeにはそれぞれ以下の制約があります。

- cidr: 0~255の整数
- creator: 1文字以上の半角英数
- cpuType: arm64またはx86_64
- instanceSize: mediumまたはlarge
