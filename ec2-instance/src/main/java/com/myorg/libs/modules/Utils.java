package com.myorg.libs.modules;

import software.amazon.awscdk.services.ec2.AmazonLinuxCpuType;
import software.amazon.awscdk.services.ec2.InstanceClass;
import software.amazon.awscdk.services.ec2.InstanceSize;
import software.amazon.awscdk.services.eks.CpuArch;

public class Utils {
    /**
     * StringをEnumにする
     * @param cpuType cdk.jsonから受け取ったcpuType
     * @return AmazonLinuxCpuType
     */
    static public AmazonLinuxCpuType getCpuType(String cpuType) {
        if(cpuType.toLowerCase().equals(CpuArch.X86_64.toString().toLowerCase())) {
            return AmazonLinuxCpuType.X86_64;
        }else {
            return AmazonLinuxCpuType.ARM_64;
        }
    }

    /**
     * cpuTypeをもとにインスタンスクラスを決める
     * @param cpuType cdk.jsonから受け取ったcpuType
     * @return InstanceClass
     */
    static public InstanceClass getInstanceClass(String cpuType) {
        if(cpuType.equals(CpuArch.X86_64.toString())) {
            return InstanceClass.T3;
        }else {
            return InstanceClass.C7G;
        }
    }

    /**
     * StringをEnumにする
     * @param instanceSize cdk.jsonから受け取ったinstanceSize
     * @return InstanceSize
     */
    static public InstanceSize getInstanceSize(String instanceSize) {
        switch (instanceSize.toLowerCase()) {
            case "medium":
                return InstanceSize.MEDIUM;
            case "large":
                return InstanceSize.LARGE;
            default:
                return InstanceSize.MEDIUM;
        }  
    }
}
