[Complete graph](http://en.wikipedia.org/wiki/Complete_graph)
==============
![Image of graph](http://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Complete_graph_K7.svg/200px-Complete_graph_K7.svg.png)

[Live test](http://tinyurl.com/cznz46l)

This query is a bit of a cheat. A root node is created,
and used to hang a number of nodes from. I then match
two nodes hanging from the center, with the requirement
that the id of the first be less than the id of the next.
This is to prevent double relationships and self 
relationships.
Using said match, I create relationships between all the nodes
Last comes the cheat - I remove the center node and all 
relationships connected to it.

    create center
    foreach( x in range(1,10) : 
       create leaf={count : x}, center-[:X]->leaf
    )
    with center
    match leaf1<--center-->leaf2
    where id(leaf1)<id(leaf2)
    create leaf1-[:X]->leaf2
    with center
    match center-[r]->()
    delete center,r;