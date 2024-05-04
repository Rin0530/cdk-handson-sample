package com.myorg;

import java.util.Map;

import com.myorg.libs.Ec2Props;
import com.myorg.libs.Ec2Stack;
import com.myorg.libs.VpcProps;
import com.myorg.libs.VpcStack;
import com.myorg.libs.modules.Validate;

import software.amazon.awscdk.Tags;


public class App {
    public static void main(final String[] args) {
        software.amazon.awscdk.App app = new software.amazon.awscdk.App();

        @SuppressWarnings("unchecked")
        Map<String,String> context = (Map<String,String>)app.getNode().tryGetContext("env");
       
        Validate.validate(context);

        VpcStack vpc = new VpcStack(
            app, 
            String.format("VpcStack-%s", context.get("creator")),
            VpcProps.builder()
                .cidr(context.get("cidr"))
                .creator(context.get("creator"))
                .build()
        );

        new Ec2Stack(
            app, 
            String.format("Ec2Stack-%s", context.get("creator")), 
            Ec2Props.builder()
                .vpc(vpc.getVpc())
                .sshSecurityGroup(vpc.getSecurityGroup())
                .cpuType(context.get("cpuType"))
                .instanceSize(context.get("instanceSize"))
                .creator(context.get("creator"))
                .count(context.get("count"))
                .build()
        );

        Tags.of(app).add("Creator", context.get("creator"));
        
        app.synth();
    }
}

