from aws_cdk import (
    Stack
)

from aws_cdk.aws_ec2 import (
    Vpc,
    IpAddresses,
    SubnetConfiguration,
    SubnetType,
    SecurityGroup,
    Peer,
    Port
)

from constructs import Construct

class VpcStack(Stack):
        
    def __init__(self, scope: Construct, id: str, cidr: str, creator: str, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)
        
        # Create a VPC with public subnets in 2 AZs
        self.vpc = Vpc(self, f"VPC-{creator}",
            ip_addresses=IpAddresses.cidr(f"10.{int(cidr)}.0.0/16"),
            nat_gateways=0,
            subnet_configuration=[
                SubnetConfiguration(
                    cidr_mask=24,
                    name="PublicSubnet",
                    subnet_type=SubnetType.PUBLIC,
                    map_public_ip_on_launch=True
                )
            ],
            max_azs=2
        )
        
        # Create a security group for SSH
        self.sshSecurityGroup = SecurityGroup(self, f"SSHSecurityGroup-{creator}",
            vpc = self.vpc,
            description="Security Group for SSH",
            allow_all_outbound=True
        )

        # Allow SSH inbound traffic on TCP port 22
        self.sshSecurityGroup.add_ingress_rule(Peer.any_ipv4(),Port.tcp(22))
