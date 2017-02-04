# Definition for supervised learning
Finding a function that maps some input to an output that gives you 
reasonable result according to a training set.

* Input: Vector of real values. Usually x
* Output: Vector of real values. Usually y
* Training set: 
  * Input values. Matrix of real values. 
    * Rows: Input values of one training example.
    * Columns: Features 
  * Output values Matrix of real values. 
    * Rows: Output values of one training example. 
    * Columns: Output dimensions

The nth row ouf the input matrix corresponds to the nth row of the input matrix. One row of 

To map input to output you use a function which behaviour is controlled by parameter. This 
function is callled the hypothesis. It can be any kind of function. In machinelearning 
polinomial functions are often used. 

# Learning in general
The process of learning is to find the parameters of your hypothesis to get a function
that produces reasonable outputs for inputs it did not see before. That means for
inputs that where not included in the training set.

The general learning process is to:
* Choose a random set of parameters 
* Calculate the output for all input values of the training set unsing the hypothesis with the current 
parameters. 
* Find a new set of parameters that improve the behaviour of the hypothesis.
* Go back to the 'Calculate the output ...' step. and continue as long untill the parameters do not 
change any longer.

# Learning using a cost function
The tricky step i the above described learning algorithm is to find a new set of parameters that 
give you better results according to your training set. One way to find better parameters is the use of a
cost function and to find a minimum for that cost function.

## Cost function
The cost function is a function that is small if the calculated output values are close to the
output values of the training set for each training example and great if there are big 
differences between the calculated and the expected values. 

## Types of supervised learning problems

There are two types of learning problems. The main difference between the different types is
their cost function.

### Linear regression
An input vector is mapped to an output vector of real values.

```octave
TODO: vectorized cost function in octave
```

* Theta: Current parameters of the hypothesis. Matrix of Real values
* X: Training set input values
* Y: Training set output values
* h(...): Hypothesis function taking n input values and containing n parameters. Returns k (calculated) output values.
* m: Number of training examples
* n: Number of features (input)
* k: Number of dimensions (output)

Partial derivaties of the cost function.

```octave
TODO: Vectorized derivaty in octave
```

### Logistic regression
Categorisation problem. An input vector is mapped to one of k categories. 

```octave
TODO: vectorized cost function in octave
```

* Theta: Current parameters of the hypothesis. Matrix of Real values
* X: Training set input values
* Y: Training set output values
* h(...): Hypothesis function taking n input values and containing n parameters. Returns k (calculated) output values.
* m: Number of training examples
* n: Number of features (input)
* k: Number of dimensions (output)

Partial derivaties of the cost function.

```octave
# TODO: Vectorized derivaty in octave
```

## Learning algorithms
In order to find the minimum of the above described cost functions you must apply the hypotesis and the 
partial deriverties for each input feature to a learning algorithm in order to find the optimal
parameters according to your training set.

```octave
Theta_opt = learn(Theta_initial, cf, cf', j) 
```

* Theta_opt: Optimized parameters for your cost function. Vector of reals.
* Theta_initial: Initial parameters for your cost function. Vector of reals. Con be the null vector.
* cf: Cost function. Function that calculates output from input and parameters. y = cf(x, Theta)
* cf': List of functions for the partial deriverties of the cost function on every feature. y' = cf'(x, Theta)
* j: Number of iterations

Learning algorithems that might be used:
* Gradient descent
* ??? TODO
* ???

## Multilayer neuronal nets
A neuronal can be seen as a special kind of hypothes. As for polinomial hypothesis or other nonlinear functions the
cost and the partial derivative can be calculated for each training sample.

### Forward propagation
Calculates the cost for one training sample.

```octave
# TODO: Vectorized form for forward propagation in octave.
```

### Backward propagation
Calculates the partial derivatieves for each training example.

```octave
# TODO: Vectorized form for backward propagation in octave.
```

### Applying learning algorithems to neuronal networks
Other than for polinomial functions the parameters are not in a single matrix but in a list of matrixes (one for teh calculation of each layer).
In order to use the above mentioned Learning algorithms you have to flatten the parameters into one vector and afterwards 
you must reload the optimized parameters into patrixes of the original sizes. 

```octave
# TODO: Vectorized implementation of forward and backward propagation in octave.
```

## Support vector machine
Another algorithem for logistic regressin is the Support Vector Machine (SVM) also called Broad Margin Optimizer TODO chech if true.

TODO Describe and write down the octave code for the support vector machine

# Optimisations to learning algorithms

## Normalisation
Linear transformations to the feature values leads to the same parameters. As the performance of the learning algorithem is better having features that
vary in the same range it makes sense to apply linear transformations to achive that goal.

```octave
x1 = (x .- m(x)) ./ v(x) #TODO Check if correct. TODO change to a vectorized version that operates on X (Matrix of input values).

x     ... Input values. Vector of reals
x1    ... Normalized input values. Vector of reals.
m(x)  ... Arithmetic mean of the input values
v(x)  ... Variance of the input values
```

## Regularisation
If your hypothes is a very 'advanced' function (e.g. a high graded polinomial) it might fit your training set very well but perform poor
to predict output values for other inputs. To avoid this problem an extra regulasition term can be added to the cost function in order to
lessen that problem.

```octave
Jr(theta) = J(theta) + R # TODO Write the regularisation term in vectorized octave

Jr()  ... Regularized cost function
J()   ...Original cost function
```

TODO: Check if there is an impact on the derivatives.

## Dimension reduction
Having lots of features (e.g 1000 or more) can lead to a poor performance of the learning algorithm. 
In practice several of these featuresa do have an almost linear relationship. These features can be 
reduced to a single feature that represents the behaviour of those closly related features.

Another use case for PCA is visualisation of multidimensional data by redusing to 2 or 3 dimensions.

The algorithem for dimension reduction is PCA (principal component analysis). It is based on the application 
of covariance eigenvectors. 

```octave
sigma = (1 / m) * X' * X # Calculate covariance 
[U,S,V] = svd(Sigma);    # Calculate the eigenvector
Ureduce = U(:,1:k);      # Select the first k features of U

Ureduce ... The reduced set of features. 
k       ... The number of features after reduction
m       ... The number of features before reduction
X       ... The original set uf features. Mmatrix (m x n)
n       ... Number of learning examples TODO Better expression for learning examples
sigma   ... Covariance matrix of X. M(m x m)
svd()   ... Calculation of the singular value decomposition. (something like eigenvector) 
U       ... Reduced set of features containing all levels of reduction
S       ... Matrix containing values for calculating the 'retained variance' 
V       ... ??? Not needed for PCA
```

### Result examination
In order to find out if you have choosen the amount for k you should measuer the
vaiance of the reduced dtaset. This can be done with the S vector which is one of the 
return values of the svd function.

```octave
v = 
```
# Result examination

After you have optimized the parameters of the hypothesis it is necessary 
to examine the behaviour of that function. There are several indexes that give 
you an idea how well your optimized hypothesis can predict meaningfull 
output values.

## Bios

TODO

## Variance

TODO

## Precission and Recall

TODO

# Other machine learning Algorithms

## Clustering with the K-Means algorithm
Another topic of unsuperwised learning is clustering. It finds a predefined number of
clusters in an arbritary n dimensional dataset. 

The K-Means algorithm works in the following way.

1. Select k random points (centroids) in your data space.
2. Measure the distence from all the data points to all centroids and choose assign
   each point to the closest centroid to form k clusters.
3. Find the aritmetic mean of each cluster and move its centroid to that position
4. Continue at Step 2 until the centroids do not move (a lot) any longer in Step 3.

```octave
// Initialize values
J = some number
K = some number
X = some Matrix(m x n)       
C = some Matrix(K x n)  
M = length(X)
idx = zeros(M, 1);                                 # Result. Vector of length m
for j = 1 : J
  for m = 1 : M;
  	dx = zeros(K, 1);
  	for k = 1 : K;
  		dx(k) = sum(sum((X(m, :) - C(k, :)) .^ 2 )); # calculate distance
  	end
  	[_, i] = min(dx);                              # find min distance
  	idx(m) = i;                                    # find number of centroid
  end
for i = 1 : K;
  clust = X([find(idx == i)], :);
  C(i, :) = mean(clust);
end

M     ... Number of training samples 
J     ... Number of iterations
K     ... Number of centroids
X     ... Input data. Matrix(n x M) 
C     ... Centroids. Matrix(n x K)
idx   ... Output containing the corresponding centroid for each data sample
```

