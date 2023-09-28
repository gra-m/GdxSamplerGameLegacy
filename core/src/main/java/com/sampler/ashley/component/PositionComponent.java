package com.sampler.ashley.component;

import com.badlogic.ashley.core.Component;

// Component concept is best practice OO concept, do not have a BigDecimal named money have a class named money
// containing a BigDecimal. Here floats x and y become PositionComponent,
// a component to be added to any game entity that has/needs position.
public class PositionComponent implements Component
{
    public float x;
    public float y;
    
}
