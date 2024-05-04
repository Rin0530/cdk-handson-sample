from aws_cdk import (
    CfnOutput,
    Stack
)
from aws_cdk.aws_ec2 import (
    AmazonLinuxEdition,
    BlockDevice,
    BlockDeviceVolume,
    EbsDeviceProps,
    EbsDeviceVolumeType,
    Instance,
    InstanceType,
    MachineImage,
    KeyPair,
    SecurityGroup,
    Vpc
)
from constructs import Construct

from libs.modules.utils import (
    getCpuType,
    getInstanceClass,
    getInstanceSize
)

class Ec2Stack(Stack):
        
    def __init__(self, 
            scope: Construct,
            id: str,
            vpc:Vpc,
            sshSecurityGroup:SecurityGroup,
            cpuType:str,
            instanceSize:str,
            creator:str,
            count:str,
            **kwargs
        ) -> None:
        super().__init__(scope, id, **kwargs)
        
        keyPair = KeyPair(self,id=f"sshKey-{creator}")
        
        for i in range(1,int(count)+1):
            # Create EC2 Instance
            self.instance = Instance(self, f"Ec2Instance-{creator}{i}",
                vpc=vpc,
                machine_image=MachineImage.latest_amazon_linux2023(
                    cached_in_context=False,
                    cpu_type=getCpuType(cpuType),
                    edition=AmazonLinuxEdition.STANDARD
                ),
                security_group=sshSecurityGroup,
                key_pair=keyPair,
                instance_type=InstanceType.of(
                    instance_class=getInstanceClass(cpuType),
                    instance_size=getInstanceSize(instanceSize)
                )
            )
            
            # SSH Command to connect to the EC2 Instance
            CfnOutput(self, f"sshCommand-{creator}{i}",
                value=f'ssh ec2-user@{self.instance.instance_public_dns_name}'
            )
            
        CfnOutput(self, "keyPair",
                value=f'aws ssm get-parameter --name /ec2/keypair/${keyPair.key_pair_id} --with-decryption --query Parameter.Value --output text'
            )