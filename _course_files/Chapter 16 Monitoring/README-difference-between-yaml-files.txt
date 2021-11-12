We've supplied two verisons of the yaml for monitoring - for EKS and Kops.

What's the difference between the two?

At the time of writing (November 2021), actually there is no difference between the two. We previously needed to maintain separate versions. 

However, I've recently made changes to the yaml which now means there's no difference. I'm keeping the files separate so that the videos don't need to be re-recorded!

The changes were made due to changes in the latest versions of Kubernetes, and I wanted to make it so there was no impact on the course videos.

BUT:
 
If you are planning to deploy to a production *Kops* cluster (EKS is fine), please note that the version shipped here has removed several control plane alerts. I needed to remove these alerts because Kops users would get spurious control plane (master) alerts which can only be silenced via an awkward workaround (see below).

So you could install my kops-monitoring.yaml to your cluster, but there's a chance you might be missing out on some valuable alerts. So I recommend for a production cluster, get the latest version of the monitoring stack (with all alerts enabled) from https://github.com/prometheus-community/helm-charts/tree/main/charts/kube-prometheus-stack

The section in the course on "Helm" explains how to generate the yaml from their Helm chart.

Then you would need to make a change to your cluster as follows:

kops edit cluster

Then insert the following two lines, as a child element of "spec:"

spec:
.
.
  kubeProxy:
    metricsBindAddress: 0.0.0.0

After doing this, you will need to run "kops update cluster --yes" and then run "kops rolling-update cluster". This will terminate all the nodes in your cluster one by one (so the only one node is unavailable at any one time), and will make the changes needed.

Full details of the reason behind this (it is quite obscure) here: https://github.com/helm/charts/issues/16476

Without doing this, you will get lots of spurious alerts about the control plane.
