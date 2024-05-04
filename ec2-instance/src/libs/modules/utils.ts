import { AmazonLinuxCpuType, InstanceClass, InstanceSize } from 'aws-cdk-lib/aws-ec2';

/**
 * cdk.jsonのcontext
 */
export interface Context {
  cidr: string,
  creator: string,
  cpuType: string,
  instanceSize: string,
  count: string,
}

/**
 * StringをEnumにする
 * @param cpuType cdk.jsonから受け取ったcpuType
 * @return AmazonLinuxCpuType
 */
export function getCpuType(cpuType: string) {
  if (cpuType.toLowerCase() == AmazonLinuxCpuType.X86_64) {
    return AmazonLinuxCpuType.X86_64
  } else {
    return AmazonLinuxCpuType.ARM_64
  }
}

/**
 * cpuTypeをもとにインスタンスクラスを決める
 * @param cpuType cdk.jsonから受け取ったcpuType
 * @return InstanceClass
 */
export function getInstanceClass(cpuType: string) {
  if (cpuType.toLowerCase() == AmazonLinuxCpuType.X86_64) {
    return InstanceClass.T3
  } else {
    return InstanceClass.C7G
  }
}

/**
 * StringをEnumにする
 * @param instanceSize cdk.jsonから受け取ったinstanceSize
 * @return InstanceSize
 */
export function getInstanceSize(instanceSize: string) {
  switch (instanceSize.toLowerCase()) {
    case 'medium':
      return InstanceSize.MEDIUM;
    case 'large':
      return InstanceSize.LARGE;
    default:
      return InstanceSize.MEDIUM;
  }
}