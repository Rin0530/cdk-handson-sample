import { App, Tags } from 'aws-cdk-lib';
import { Ec2Stack } from './libs/ec2Stack';
import { VpcStack } from './libs/vpcStack';
import  { Context } from './libs/modules/utils';
import { validateContext } from './libs/modules/validate';

const app = new App();

const context:Context = app.node.tryGetContext("env")

validateContext(context)

// Create VPC
const vpcStack = new VpcStack(app, `VpcStack-${context.creator}`, {
    cidr: Number(context.cidr),
    creator: context.creator
});

// Create EC2 Instance
new Ec2Stack(app, `Ec2Stack-${context.creator}`, {
    vpc: vpcStack.vpc,
    sshSecurityGroup: vpcStack.sshSecurityGroup,
    cpuType: context.cpuType,
    instanceSize: context.instanceSize,
    creator: context.creator,
    count: context.count
});

// スタックのリソースに作成者のタグをつける
Tags.of(app).add("Creator", context.creator);

app.synth();