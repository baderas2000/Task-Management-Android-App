package com.example.se2_team06.viewmodel;

import com.example.se2_team06.model.Sketch;

import java.util.Vector;

public class SketchViewModel {

    private final Vector<Sketch> sketch;

    public SketchViewModel(Vector<Sketch> sketch){
        this.sketch =sketch;
    }

    public Vector<Sketch> getSketch() {
        return sketch;
    }
    public  void addSketch(){

    }
    public void deleteSketch(){

    }
}
