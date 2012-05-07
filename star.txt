// http://en.wikipedia.org/wiki/Star_(graph_theory)
// Live: http://tinyurl.com/84wbkl6

// Works by first creating a center node, and then once
// per element in the range, creates a leaf and connects
// it to the center

create center
foreach( x in range(1,10) : 
   create leaf, center-[:X]->leaf
)
return center;