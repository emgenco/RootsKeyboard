package com.usna.ryankelley.rootskeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.nfc.Tag;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

/**
 * Created by ryankelley on 1/19/15.
 */
public class RootsKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener{

    private KeyboardView kv;
    private Keyboard keyboard;
    private boolean caps = false;

    @Override
    public View onCreateInputView() {
        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.roots);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }

    @Override
    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        switch(attribute.inputType&EditorInfo.TYPE_MASK_CLASS){
            case EditorInfo.TYPE_CLASS_TEXT:
                int variation = attribute.inputType & EditorInfo.TYPE_MASK_VARIATION;
                if(variation==EditorInfo.TYPE_TEXT_VARIATION_PASSWORD) {
                    keyboard = new Keyboard(this, R.xml.roots);
                    kv.setKeyboard(keyboard);
                }
                else {
                    keyboard = new Keyboard(this, R.xml.qwerty);
                    kv.setKeyboard(keyboard);
                }

                break;
            default:
                keyboard = new Keyboard(this, R.xml.qwerty);
                kv.setKeyboard(keyboard);
        }

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes){
        InputConnection ic = getCurrentInputConnection();
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                //ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                requestHideSelf(0);
                break;
            case -7:
               keyboard = new Keyboard(this, R.xml.roots2);
               kv.setKeyboard(keyboard);
               break;
            case -8:
               keyboard = new Keyboard(this, R.xml.roots3);
               kv.setKeyboard(keyboard);
               break;
            case -10:
                keyboard = new Keyboard(this, R.xml.roots);
                kv.setKeyboard(keyboard);
                break;

            default:
                char code = (char)primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);

        }
    }
    @Override
    public void onPress(int primaryCode){

    }

    @Override
    public void onRelease(int primaryCode){

    }
    @Override
    public void onText(CharSequence text){

    }
    @Override
    public void swipeDown(){

    }
    @Override
    public void swipeLeft(){

    }
    @Override
    public void swipeRight(){

    }
    @Override
    public void swipeUp(){

    }

}
