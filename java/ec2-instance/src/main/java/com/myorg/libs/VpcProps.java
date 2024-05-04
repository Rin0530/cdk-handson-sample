package com.myorg.libs;

import software.amazon.awscdk.StackProps;

public interface VpcProps extends StackProps {
    int getCidr();
    String getCreator();

    static Builder builder() {
        return new Builder();
    }

    class Builder {
        private int cidr;
        private String creator;

        public Builder cidr(final String cidr) {
            this.cidr = Integer.parseInt(cidr);
            return this;
        }

        public Builder creator(final String creator) {
            this.creator = creator;
            return this;
        }

        public VpcProps build() {
            return new VpcProps() {
                @Override
                public int getCidr() {
                    return Builder.this.cidr;
                }

                @Override
                public String getCreator() {
                    return Builder.this.creator;
                }
            };
        }
    }
}