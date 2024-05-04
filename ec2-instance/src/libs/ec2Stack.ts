/* eslint-disable import/no-extraneous-dependencies */
import { Stack, StackProps, CfnOutput } from 'aws-cdk-lib';
import {  getCpuType, getInstanceClass, getInstanceSize, } from './modules/utils';
import { AmazonLinuxEdition, Instance, InstanceType, KeyPair, MachineImage, SecurityGroup, Vpc } from 'aws-cdk-lib/aws-ec2';
import { Construct } from 'constructs';

export interface Ec2Props extends StackProps {
  vpc: Vpc;
  sshSecurityGroup: SecurityGroup;
  cpuType: string;
  instanceSize: string;
  creator: string;
  count: string
}

export class Ec2Stack extends Stack {
  constructor(scope: Construct, id: string, props: Ec2Props) {
    super(scope, id, props);

    const keyPair = new KeyPair(this, `sshKey-${props.creator}`)

    for (let i = 1; i < Number(props.count)+1; i++) {
      // Create EC2 Instance
      const instance = new Instance(this, `Ec2Instance-${props.creator}${i}`, {
        vpc: props.vpc,
        machineImage: MachineImage.latestAmazonLinux2023({
          cachedInContext: false,
          cpuType: getCpuType(props.cpuType),
          edition: AmazonLinuxEdition.STANDARD,
        }),
        securityGroup: props.sshSecurityGroup,
        keyPair: keyPair,
        instanceType: InstanceType.of(
          getInstanceClass(props.cpuType),
          getInstanceSize(props.instanceSize)),
      });

      // SSH Command to connect to the EC2 Instance
      new CfnOutput(this, `sshCommand-${props.creator}${i}`, {
        value: `ssh ec2-user@${instance.instancePublicDnsName}`,
      });
    }

    new CfnOutput(this, `keyPair`, {
      value: `aws ssm get-parameter --name /ec2/keypair/${keyPair.keyPairId} --with-decryption --query Parameter.Value --output text`,
    });
  }
}
