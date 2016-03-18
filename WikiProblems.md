there are some snippets of the code that might work  for the Torch


//Turn on
camera = Camera.open();
Parameters p = camera.getParameters();
p.setFlashMode(Parameters.FLASH\_MODE\_TORCH);
camera.setParameters(p);
camera.startPreview();

//Turn off
camera = Camera.open();
Parameters p = camera.getParameters();
p.setFlashMode(Parameters.FLASH\_MODE\_OFF);
camera.setParameters(p);
camera.stopPreview();