import { Stack, StackProps } from 'aws-cdk-lib';
import {
  SecurityGroup,
  Peer,
  Port,
  SubnetType,
  Vpc,
  IpAddresses
} from 'aws-cdk-lib/aws-ec2';
import { Construct } from 'constructs';

export interface VpcProps extends StackProps {
  cidr: number;
  creator: string;
}

export class VpcStack extends Stack {
  public readonly sshSecurityGroup: SecurityGroup;
  public readonly vpc: Vpc;

  constructor(scope: Construct, id: string, props:VpcProps) {
    super(scope, id);


    // Create a VPC with public subnets in 2 AZs
    this.vpc = new Vpc(this, `VPC-${props.creator}`, {
      ipAddresses: IpAddresses.cidr(`10.${props.cidr}.0.0/16`),
      natGateways: 0,
      subnetConfiguration: [
        {
          cidrMask: 24,
          name: 'PublicSubnet',
          subnetType: SubnetType.PUBLIC,
          mapPublicIpOnLaunch: true,
        },
      ],
      maxAzs: 2,
    });

    // Create a security group for SSH
    this.sshSecurityGroup = new SecurityGroup(this, `SSHSecurityGroup-${props.creator}`, {
      vpc: this.vpc,
      description: 'Security Group for SSH',
      allowAllOutbound: true,
    });

    // Allow SSH inbound traffic on TCP port 22
    this.sshSecurityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(22));
  }
}
