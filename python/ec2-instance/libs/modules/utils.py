from aws_cdk.aws_ec2 import (
    AmazonLinuxCpuType,
    InstanceClass,
    InstanceSize
)

def getCpuType(cpuType:str)-> AmazonLinuxCpuType:
    if cpuType == AmazonLinuxCpuType.X86_64.name:
        return AmazonLinuxCpuType.X86_64
    else:
        return AmazonLinuxCpuType.ARM_64
    
def getInstanceClass(cpuType:str) -> InstanceClass:
    if cpuType == AmazonLinuxCpuType.X86_64.name:
        return InstanceClass.T3
    else:
        return InstanceClass.C7G
    
def getInstanceSize(instanceSize:str) -> InstanceSize:
    if instanceSize.lower() == 'medium':
        return InstanceSize.MEDIUM
    elif instanceSize.lower() == 'large':
        return InstanceSize.LARGE
    else:
        return InstanceSize.MEDIUM