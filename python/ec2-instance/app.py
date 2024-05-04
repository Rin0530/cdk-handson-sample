from aws_cdk import App, Tags

from libs.vpcStack import VpcStack
from libs.ec2Stack import Ec2Stack
from libs.modules.validate import validateContext

app = App()

context = dict(app.node.try_get_context("env"))

validateContext(context)

vpc = VpcStack(app, f"VpcStack-{context.get('creator')}",
    cidr=context.get("cidr"),
    creator=context.get("creator")
)

Ec2Stack(app, f"Ec2Stack-{context.get('creator')}",
    vpc=vpc.vpc,
    sshSecurityGroup=vpc.sshSecurityGroup,
    cpuType=context.get("cpuType"),
    instanceSize=context.get("instanceSize"),
    creator=context.get("creator"),
    count=context.get("count")
)

Tags.of(app).add("Creator", context.get("creator"))

app.synth()
