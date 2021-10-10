package com.example.projectar;

import android.content.Context;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.Pose;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.util.concurrent.CompletableFuture;

public class AugmentedImageNode extends AnchorNode {

    private static final String TAG = "AugmentedImageNode";

    // The augmented image represented by this node.
    private AugmentedImage image;
    //เพิ่ม
    private static CompletableFuture<ModelRenderable> modelRenderableCompletableFuture;

    public AugmentedImageNode(Context context, int modelId) {

        System.out.println("test AugmentedImageNode "+ modelId);

//    if (modelRenderableCompletableFuture == null){

        modelRenderableCompletableFuture = ModelRenderable.builder()
                .setRegistryId("my_model")
                .setSource(context, modelId)
                .build();
//    }
    }

    public void setImage(AugmentedImage image){

        this.image = image;
        if (!modelRenderableCompletableFuture.isDone()){
            CompletableFuture.allOf(modelRenderableCompletableFuture)
                    .thenAccept((Void aVoid) ->{
                        setImage(image);
                    }).exceptionally(throwable ->{
                return null;
            });
        }
        setAnchor(image.createAnchor(image.getCenterPose()));
        Node node = new Node();
        Pose pose = Pose.makeTranslation(0.0f,0.0f, 0.25f);

        node.setParent(this);
        node.setLookDirection(new Vector3(pose.tx(), pose.ty(), pose.tz()));
        node.setLocalRotation(new Quaternion(pose.qx(), pose.qy(), pose.qz(), pose.qw()));
        node.setRenderable(modelRenderableCompletableFuture.getNow(null));
    }

    public AugmentedImage getImage() {
        return image;
    }
}
