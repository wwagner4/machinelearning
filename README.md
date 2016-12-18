# machinelearning
Resources concerning the coursera course [machine learning](https://www.coursera.org/learn/machine-learning/) held by Andrew Ng of the stanford.

## Breeze
I a scala library for mathematic operations needed for machine learning.

Home: [https://github.com/scalanlp/breeze](https://github.com/scalanlp/breeze)

### Installation
Using the version 0.12 did not work using sbt and the provided binaries. Problems occured (resource not found) using the
native part. Downloading the source of the release and compiling it locally fixed the problem. 

### Performance
On my macbook pro inverting a nxn matrix containing random values rsulted in the foollowing runtimes.
```text
inv     5 x     5         100 milis
inv     5 x     5           0 milis
inv    10 x    10           0 milis
inv   100 x   100           1 milis
inv   200 x   200           4 milis
inv   500 x   500          40 milis
inv  1000 x  1000         303 milis
inv  2000 x  2000        2159 milis
inv  5000 x  5000       30413 milis
```
