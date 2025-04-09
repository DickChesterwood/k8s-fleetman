#!/usr/bin/env bash

# Only cluster name needs to be set - existing cluster only.

if [[ -z "$CLUSTER_NAME" ]]; then
    echo "Please set CLUSTER_NAME in environment" 1>&2
    exit 1
fi

# We need a role name, doesn't matter what this is
role_name=EBS_CSI_DRIVER_ROLE_${CLUSTER_NAME}

export region=$(aws configure get region)

export account_id=$(aws sts get-caller-identity --query Account --output text)

export oidc_id=$(aws eks describe-cluster --name $CLUSTER_NAME --query "cluster.identity.oidc.issuer" --output text | cut -d '/' -f 5)

eksctl utils associate-iam-oidc-provider --cluster $CLUSTER_NAME --approve

# This sets up a policy file as described in the docs - this will change the account id, oidc_id and region
sudo yum install -y gettext
envsubst < policy.json.template > policy.json
aws iam create-role --role-name ${role_name} --assume-role-policy-document file://policy.json
aws iam attach-role-policy --policy-arn arn:aws:iam::aws:policy/service-role/AmazonEBSCSIDriverPolicy --role-name ${role_name}

#create the plugin
role_arn=$(aws iam get-role  --role-name ${role_name} --query 'Role.[Arn]' --output text)
eksctl create addon --cluster  ${CLUSTER_NAME} --name aws-ebs-csi-driver --version latest --service-account-role-arn ${role_arn} --force
eksctl utils migrate-to-pod-identity --cluster ${CLUSTER_NAME} --approve

echo "Completed - Now you should be able to provision EBS volumes using standard PVCs."
