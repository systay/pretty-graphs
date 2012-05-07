// http://en.wikipedia.org/wiki/Friendship_graph
// Live test: http://tinyurl.com/6taky7t
// Works by first creating a center node, and then once
// per element in the range, creates a cycle graph C3 and connects
// it to the center

create center
foreach( x in range(1,10) : 
   create leaf1, leaf2, center-[:X]->leaf1, center-[:X]->leaf2, leaf1-[:X]->leaf2
)
return center;