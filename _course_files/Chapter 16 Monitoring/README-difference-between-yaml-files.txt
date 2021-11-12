We've supplied two verisons of the yaml for monitoring - for EKS and Kops.

What's the difference between the two?

At the time of writing (November 2021), actually there is no difference between the two. We previously needed to maintain separate versions. 

However, I've recently made changes to the yaml which now means there's no difference. I'm keeping the files separate so that the videos don't need to be re-recorded!

The changes were made due to changes in the latest versions of Kubernetes, and I wanted to make it so there was no impact on the course videos.

BUT:
 
If you are planning to deploy to a production *Kops* cluster (EKS is fine), please note that the version shipped here has removed several control plane alerts. You would probably want to get the latest version of the monitoring stack (with all alerts enabled) from their github Helm chart.

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
