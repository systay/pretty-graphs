[Star Graph](http://en.wikipedia.org/wiki/Star_(graph_theory\))
========
![Image of graph](http://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/Star_graphs.svg/500px-Star_graphs.svg.png)

[Live test](http://tinyurl.com/84wbkl6)

Works by first creating a center node, and then once
per element in the range, creates a leaf and connects
it to the center

    create center
    foreach( x in range(1,10) : 
       create leaf, center-[:X]->leaf
    )
    return center;