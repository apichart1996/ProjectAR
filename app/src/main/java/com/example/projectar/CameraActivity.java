package com.example.projectar;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.common.helpers.SnackbarHelper;
import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.Frame;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ExternalTexture;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import java.util.Collection;

import java.util.HashMap;
import java.util.Map;

public class CameraActivity extends AppCompatActivity {
    private ArFragment arFragment;
    private ImageView fitToScanView;
    private Scene scene;
    private ExternalTexture texture;
    private boolean isImageDetected = false;

    //video
    private ModelRenderable videoRenderable;
    private MediaPlayer mediaPlayer;

    private ViewRenderable TextViewrenderable;


    // Augmented image and its associated center pose anchor, keyed by the augmented image in
    // the database.
    private final Map<AugmentedImage, MediaPlayer> augmentedImageMap = new HashMap<>();
    private boolean playmedia = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

//เพิ่ม
        scene = arFragment.getArSceneView().getScene();
        fitToScanView = findViewById(R.id.image_view_fit_to_scan);
        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (augmentedImageMap.isEmpty()) {
            fitToScanView.setVisibility(View.VISIBLE);
        }
    }


    /**
     * Registered with the Sceneform Scene object, this method is called at the start of each frame.
     *
     * @param frameTime - time since last frame.
     */
    private void onUpdateFrame(FrameTime frameTime) {

        Frame frame = arFragment.getArSceneView().getArFrame();

        // If there is no frame or ARCore is not tracking yet, just return.
        if (frame == null || frame.getCamera().getTrackingState() != TrackingState.TRACKING) {

            return;
        }

        Collection<AugmentedImage> updatedAugmentedImages =
                frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage augmentedImage : updatedAugmentedImages) {
            switch (augmentedImage.getTrackingState()) {
                case PAUSED:
                    // When an image is in PAUSED state, but the camera is not PAUSED, it has been detected,
                    // but not yet tracked.
                    String text = "Detected Image " + augmentedImage.getName();
                    SnackbarHelper.getInstance().showMessage(this, text);
                    break;

                case TRACKING:
                    System.out.println("test09 TRACKING");

                    // Have to switch to UI Thread to update View.
                    fitToScanView.setVisibility(View.GONE);
//                    if (!augmentedImageMap.containsKey(augmentedImage)) {
                        if (augmentedImage.getName().equals("default.jpg")) { // content 3 D
                            Anchor anchor = augmentedImage.createAnchor(augmentedImage.getCenterPose());
                            ModelRenderable.builder()
                                    .setSource(this, Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectar-9bff3.appspot.com/o/content%2F3DModels%2FNOVELO_EARTH.sfb?alt=media&token=83cdf185-3a33-41ac-ad06-4f141ce4871c"))
                                    .build()
                                    .thenAccept(modelRenderable -> planModel01(modelRenderable, anchor));
                        }
                        if (augmentedImage.getName().equals("01.png")) { // content 3 D
                            Anchor anchor = augmentedImage.createAnchor(augmentedImage.getCenterPose());
                            ModelRenderable.builder()
                                    .setSource(this, Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectar-9bff3.appspot.com/o/content%2F3DModels%2FNOVELO_EARTH.sfb?alt=media&token=83cdf185-3a33-41ac-ad06-4f141ce4871c"))
                                    .build()
                                    .thenAccept(modelRenderable -> planModel01(modelRenderable, anchor));
                        }
                        if (augmentedImage.getName().equals("02.png") && !augmentedImageMap.containsKey(augmentedImage) ) { // content video Digital Literacy
                            System.out.println("detect image 02");
                            if (playmedia == true){
                                mediaPlayer.stop();
                                playmedia = false;
                            }
                            mediaPlayer = MediaPlayer.create(this, Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectar-9bff3.appspot.com/o/content%2Fvideo%2Fvideo02_digital_literacy.mp4?alt=media&token=62073255-1d5a-4839-afe4-5c5e824020cb"));
                            augmentedImageMap.put(augmentedImage, mediaPlayer);
                            creatMedia(mediaPlayer);
                            playVideo(augmentedImage.createAnchor(augmentedImage.getCenterPose()), augmentedImage.getExtentX(),
                                    augmentedImage.getExtentZ());
                            break;
                        }
                        if (augmentedImage.getName().equals("03.png") && !augmentedImageMap.containsKey(augmentedImage)) { // content
                            if (playmedia == true){
                                mediaPlayer.stop();
                                playmedia = false;
                            }
                            mediaPlayer = MediaPlayer.create(this, Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectar-9bff3.appspot.com/o/content%2Fvideo%2Fvideo03.mp4?alt=media&token=2fe53a23-1e94-47df-92e5-c7ac6c38012a"));
                            augmentedImageMap.put(augmentedImage, mediaPlayer);
                            creatMedia(mediaPlayer);
                            playVideo(augmentedImage.createAnchor(augmentedImage.getCenterPose()), augmentedImage.getExtentX(),
                                    augmentedImage.getExtentZ());
                            break;
                        }
                        if (augmentedImage.getName().equals("04.png") && !augmentedImageMap.containsKey(augmentedImage) ) { // content
                            if (playmedia == true){
                                mediaPlayer.stop();
                                playmedia = false;
                            }
                            mediaPlayer = MediaPlayer.create(this, Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectar-9bff3.appspot.com/o/content%2Fsounds%2F04%20(online-audio-converter.com).mp3?alt=media&token=9bc16c04-6a65-4b27-b4c7-b940893b9bf9"));
                            augmentedImageMap.put(augmentedImage, mediaPlayer);
                            creatMedia(mediaPlayer);
                            playVideo(augmentedImage.createAnchor(augmentedImage.getCenterPose()), augmentedImage.getExtentX(),
                                    augmentedImage.getExtentZ());
                            break;
                        }
                        if (augmentedImage.getName().equals("05.png") && !augmentedImageMap.containsKey(augmentedImage) ) { // content sound
                            System.out.println("test12 detect image " + augmentedImage.getName());
                            if (playmedia == true){
                                mediaPlayer.stop();
                                playmedia = false;
                            }
                            mediaPlayer = MediaPlayer.create(this, Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectar-9bff3.appspot.com/o/content%2Fsounds%2F05%20(online-audio-converter.com).mp3?alt=media&token=e91589e8-bae5-44ce-998e-c103cbc1243e"));
                            augmentedImageMap.put(augmentedImage, mediaPlayer);
                            creatMedia(mediaPlayer);
                            playVideo(augmentedImage.createAnchor(augmentedImage.getCenterPose()), augmentedImage.getExtentX(),
                                    augmentedImage.getExtentZ());
                            break;
                        }
                        if (augmentedImage.getName().equals("06.png") && !augmentedImageMap.containsKey(augmentedImage) ) { // content video
                            if (playmedia == true){
                                mediaPlayer.stop();
                                playmedia = false;
                            }
                            mediaPlayer = MediaPlayer.create(this, Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectar-9bff3.appspot.com/o/content%2Fvideo%2Fvideo06%20-%20edited.mp4?alt=media&token=4cc89779-0441-4286-8f81-53c982e6e70e"));
                            augmentedImageMap.put(augmentedImage, mediaPlayer);
                            creatMedia(mediaPlayer);
                            playVideo(augmentedImage.createAnchor(augmentedImage.getCenterPose()), augmentedImage.getExtentX(),
                                    augmentedImage.getExtentZ());
                            break;
                        }
                        if (augmentedImage.getName().equals("07.png") && !augmentedImageMap.containsKey(augmentedImage) ) { // content video
                            if (playmedia == true){
                                mediaPlayer.stop();
                                playmedia = false;
                            }
                            mediaPlayer = MediaPlayer.create(this, Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectar-9bff3.appspot.com/o/content%2Fvideo%2Fvideo07%20-%20lol_iot_smart_home.mp4?alt=media&token=400360bc-eeeb-45db-8f73-50c9b19e4038"));
                            augmentedImageMap.put(augmentedImage, mediaPlayer);
                            creatMedia(mediaPlayer);
                            playVideo(augmentedImage.createAnchor(augmentedImage.getCenterPose()), augmentedImage.getExtentX(),
                                    augmentedImage.getExtentZ());
                            break;
                        }
                        if (augmentedImage.getName().equals("08.png")) { // content 3D
                            Anchor anchor = augmentedImage.createAnchor(augmentedImage.getCenterPose());
                            ModelRenderable.builder()
                                    .setSource(this, Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectar-9bff3.appspot.com/o/content%2F3DModels%2Fmirage_solo-0.sfb?alt=media&token=c923972c-5017-4943-a6b5-fa2fc531f52f"))
                                    .build()
                                    .thenAccept(modelRenderable -> planModel(modelRenderable, anchor));

                        }
                        if (augmentedImage.getName().equals("09.png")) { // content 3 D
                            Anchor anchor = augmentedImage.createAnchor(augmentedImage.getCenterPose());
                            ModelRenderable.builder()
                                    .setSource(this, Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectar-9bff3.appspot.com/o/content%2F3DModels%2Fphone.sfb?alt=media&token=59911360-3dff-46b4-b798-609832750cf0"))
                                    .build()
                                    .thenAccept(modelRenderable -> planModel(modelRenderable, anchor));
                        }
                        if (augmentedImage.getName().equals("10.png")) { // 3D

                            System.out.println("Detect image 10");

                            Anchor anchor = augmentedImage.createAnchor(augmentedImage.getCenterPose());
                            ModelRenderable.builder()
                                    .setSource(this, Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectar-9bff3.appspot.com/o/content%2F3DModels%2FHamburger.sfb?alt=media&token=b31a1d2c-01c2-4f6b-9352-fbc2f3a13814"))
                                    .build()
                                    .thenAccept(modelRenderable -> planModel10(modelRenderable, anchor));
                        }
                        if (augmentedImage.getName().equals("11.png")) { // content 3D
                            Anchor anchor = augmentedImage.createAnchor(augmentedImage.getCenterPose());
                            ModelRenderable.builder()
                                    .setSource(this, Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectar-9bff3.appspot.com/o/content%2F3DModels%2Fdrone.sfb?alt=media&token=29343d36-8b12-4e0c-bf7f-b0d614e46e38"))
                                    .build()
                                    .thenAccept(modelRenderable -> planModel(modelRenderable, anchor));
                        }
//                    }
                    break;

                case STOPPED:
                    augmentedImageMap.remove(augmentedImage);
                    break;
            }
        }
    }


    //play video and sound
    private void creatMedia(MediaPlayer mediaPlayer) {

        texture = new ExternalTexture();
        mediaPlayer.setSurface(texture.getSurface());
        mediaPlayer.setLooping(true);
        ModelRenderable
                .builder()
                .setSource(this, Uri.parse("video_screen.sfb"))
                .build()
                .thenAccept(modelRenderable -> {
                    modelRenderable.getMaterial().setExternalTexture("videoTexture",
                            texture);
                    modelRenderable.getMaterial().setFloat4("keyColor",
                            new Color(0.01843f, 1f, 0.098f));
                    videoRenderable = modelRenderable;
                });
    }

    private void playVideo(Anchor anchor, float extentX, float extentZ) {
        mediaPlayer.start();
        playmedia = true;
        AnchorNode anchorNode = new AnchorNode(anchor);
        texture.getSurfaceTexture().setOnFrameAvailableListener(surfaceTexture -> {
            anchorNode.setRenderable(videoRenderable);
            texture.getSurfaceTexture().setOnFrameAvailableListener(null);
        });
        anchorNode.setWorldScale(new Vector3(extentX, 1f, extentZ));
        scene.onAddChild(anchorNode);
    }

    private void planModel01(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        anchorNode.setLocalScale(new Vector3(0.15f, 0.15f, 0.15f));
        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }
    private void planModel10(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        anchorNode.setLocalScale(new Vector3(0.10f, 0.10f, 0.10f));
        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }

    private void planModel(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        anchorNode.setLocalScale(new Vector3(0.15f, 0.15f, 0.15f));
        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }
}
