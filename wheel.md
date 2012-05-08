[Wheel Graph](http://en.wikipedia.org/wiki/Wheel_graph)
=============
![Image of graph](http://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Wheel_graphs.svg/200px-Wheel_graphs.svg.png)

[Live test](http://tinyurl.com/cyburot)

Works by first creating a center node, and then once
per element in the range, creates a leaf and connects
it to the center



    //First we create the center
    create center

    //Next we create all the leaf nodes and their relationships to the center
    foreach( x in range(1,10) : 
       create leaf={count:x}, center-[:X]->leaf
    )

    //Now we connect the leafs to each other. The MATCH and WHERE select the two
    //leafs that will be connected.
    with center
    match large_leaf<--center-->small_leaf
    where large_leaf.count = small_leaf.count + 1
    create small_leaf-[:X]->large_leaf

    //Now all leafs are connected, except the first and the last
    //We re-use the last match again, to find the last and first leafs
    //and then we connect them
    with center, min(small_leaf.count) as min, max(large_leaf.count) as max
    match first_leaf<--center-->last_leaf
    where first_leaf.count = min AND last_leaf.count = max
    create last_leaf-[:X]->first_leaf

    //And we're done
    return center
    