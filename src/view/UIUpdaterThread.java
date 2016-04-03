package view;

public class UIUpdaterThread extends Thread{
	
	private UIUpdater updater;
	
	public UIUpdaterThread(UIUpdater updater){
		super();
		this.updater = updater;
	}
	
	@Override
	public void run(){
		while(true){
			synchronized(this){	
				
				updater.broadcastUpdate();
				
				try{
					this.sleep(5000);
				}catch(Exception e){System.out.println(e);}
			}
		}
	}
}
