package com.myorg.libs;

import java.util.List;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.ec2.IpAddresses;
import software.amazon.awscdk.services.ec2.Peer;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;
import software.amazon.awscdk.services.ec2.SubnetType;

public class VpcStack extends Stack {
    private final SecurityGroup sshSecurityGroup;    
    private final Vpc vpc;

    public VpcStack(final Construct scope, final String id, final VpcProps props) {
      super(scope, id, props);

      // Create a new VPC
      this.vpc = Vpc.Builder.create(this, String.format("VPC-%s", props.getCreator()))
        .ipAddresses(IpAddresses.cidr(String.format("10.%d.0.0/16", props.getCidr()) ))
        .natGateways(0)
        .subnetConfiguration(List.of(SubnetConfiguration.builder()
          .name("PublicSubnet")
          .cidrMask(24)
          .subnetType(SubnetType.PUBLIC)
          .mapPublicIpOnLaunch(true)
          .build()))
        .maxAzs(2)
        .build();

      // Create a security group for SSH
      this.sshSecurityGroup = SecurityGroup.Builder.create(this, String.format("SSHSecurityGroup-%s", props.getCreator()))
        .vpc(this.vpc)
        .description("Security Group for SSH")
        .allowAllOutbound(true)
        .build();

      // Allow SSH inbound traffic on TCP port 22
      this.sshSecurityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(22));
    }

    public Vpc getVpc() {
        return this.vpc;
    }

    public SecurityGroup getSecurityGroup() {
      return this.sshSecurityGroup;
    }
}