package com.myorg.libs;

import com.myorg.libs.modules.Utils;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.ec2.AmazonLinux2023ImageSsmParameterProps;
import software.amazon.awscdk.services.ec2.AmazonLinuxEdition;
import software.amazon.awscdk.services.ec2.Instance;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ec2.KeyPair;
import software.amazon.awscdk.services.ec2.MachineImage;
import software.constructs.Construct;

public class Ec2Stack extends Stack {

    public Ec2Stack(final Construct scope, final String id, final Ec2Props props) {
      super(scope, id, props);

      KeyPair keyPair = KeyPair.Builder.create(this, String.format("sshKey-%s", props.getCreator())).build();

      for (int i = 1; i < props.getCount()+1; i++) {
        // Launch an EC2 instance in the VPC
        Instance instance = Instance.Builder.create(this, String.format("Ec2Instance-%s%d", props.getCreator(), i))
        .vpc(props.getVpc())
        .machineImage(MachineImage.latestAmazonLinux2023(
          AmazonLinux2023ImageSsmParameterProps.builder()
            .cachedInContext(false)
            .cpuType(Utils.getCpuType(id))
            .edition(AmazonLinuxEdition.STANDARD)
            .build()
        ))
        .securityGroup(props.getSecurityGroup())
        .keyPair(keyPair)
        .instanceType(InstanceType.of(
          Utils.getInstanceClass(props.getCpuType()),
          Utils.getInstanceSize(props.getInstanceSize())
        ))
        .build();

        CfnOutput.Builder.create(this, String.format("sshCommand-%s%d", props.getCreator(), i))
          .value(String.format("ssh ec2-user@%s", instance.getInstancePublicDnsName()))
          .build();
      }
      
      CfnOutput.Builder.create(this, "keyPair")
      .value(String.format("aws ssm get-parameter --name /ec2/keypair/%s --with-decryption --query Parameter.Value --output text", keyPair.getKeyPairId()))
      .build();
    }
}
