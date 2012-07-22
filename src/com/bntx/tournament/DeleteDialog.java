package com.bntx.tournament;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;

public class DeleteDialog extends AlertDialog {

	public DeleteDialog(Context context, String itemName) {
		super(context);

		this.setTitle("Delete " + itemName + "?");
		this.setButton("delete", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Log.d("Delete", "confirmed");
				if(deletable != null) {
					deletable.delete();
				}
			}
		});
		this.setButton2("cancel", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Log.d("Delete", "cancelled");
			}
		});
	}

	/**
	 * @return the deletable
	 */
	public Deletable getDeletable() {
		return deletable;
	}

	/**
	 * @param deletable the deletable to set
	 */
	public void setDeletable(Deletable deletable) {
		this.deletable = deletable;
	}

	public interface Deletable {
		public void delete();
	}
	
	private Deletable deletable;
}
