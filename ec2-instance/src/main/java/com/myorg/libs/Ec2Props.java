package com.myorg.libs;

import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.ISecurityGroup;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.Vpc;

public interface Ec2Props extends StackProps {
    IVpc getVpc();
    ISecurityGroup getSecurityGroup();
    String getCpuType();
    String getInstanceSize();
    String getCreator();
    int getCount();
    
    static Builder builder() {
        return new Builder();
    }

    class Builder {
        private Vpc vpc;
        private SecurityGroup sshSecurityGroup;
        private String cpuType;
        private String instanceSize;
        private String creator;
        private int count;

        public Builder vpc(final Vpc vpc) {
            this.vpc = vpc;
            return this;
        }

        public Builder sshSecurityGroup(final SecurityGroup sshSecurityGroup) {
            this.sshSecurityGroup = sshSecurityGroup;
            return this;
        }

        public Builder cpuType(String cpuType) {
            this.cpuType = cpuType;
            return this;
        }

        public Builder instanceSize(String instanceSize) {
            this.instanceSize = instanceSize;
            return this;
        }

        public Builder creator(String creator) {
            this.creator = creator;
            return this;
        }

        public Builder count(String count) {
            this.count = Integer.parseInt(count);
            return this;
        }

        public Ec2Props build() {
            return new Ec2Props() {
                @Override
                public IVpc getVpc(){
                    return Builder.this.vpc;
                }

                @Override
                public ISecurityGroup getSecurityGroup() {
                    return Builder.this.sshSecurityGroup;
                }

                @Override
                public String getCpuType() {
                    return Builder.this.cpuType;
                }

                @Override
                public String getInstanceSize() {
                    return Builder.this.instanceSize;
                }

                @Override
                public String getCreator() {
                    return Builder.this.creator;
                }

                @Override
                public int getCount() {
                    return Builder.this.count;
                }
            };
        }
    }
}
