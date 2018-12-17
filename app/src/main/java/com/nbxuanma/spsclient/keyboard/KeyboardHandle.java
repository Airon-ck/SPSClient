package com.nbxuanma.spsclient.keyboard;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.WeakHashMap;
/**
 * Created by Airon on 2018/11/22.
 */

public class KeyboardHandle implements OnKeyboardActionListener, TextWatcher, View.OnFocusChangeListener {

    private final HashMap<Keyboard, String[]> KEYBOARDS = new HashMap<>();
    private final KeyboardView KEYBOARDVIEW;
    private final EditText[] EDITS;
    private final Activity ACTIVITY;
    private final KeyCallback CALLBACK;
    private ShowCallback mShowCallback;
    private HideCallback mHideCallback;
    private DeleteCallback mDeleteCallback;
    private EditEndCallback mEditEndCallback;
    private int mFocusEditView = -1;
    private String[] mArrString = null;

    //构造
    private KeyboardHandle(Activity activity, EditText[] edits, KeyboardView keyboardView, KeyCallback callback) {
        if (keyboardView == null) {
            throw new NullPointerException("keyboardView is null");
        }
        this.CALLBACK = callback;
        this.ACTIVITY = activity;
        this.KEYBOARDVIEW = keyboardView;
        this.EDITS = edits;
        init();
    }

    public static KeyboardHandle creator(Activity activity,
                                         EditText[] edits,
                                         KeyboardView keyboardView,
                                         KeyCallback callback) {
        return new KeyboardHandle(activity, edits, keyboardView, callback);
    }

    public static KeyboardHandle creator(Activity activity, EditText[] edits, KeyboardView keyboardView) {
        return new KeyboardHandle(activity, edits, keyboardView, null);
    }

    //外部方法
    private void init() {
        KEYBOARDVIEW.setEnabled(true);
        KEYBOARDVIEW.setPreviewEnabled(true);
        KEYBOARDVIEW.setOnKeyboardActionListener(this);
        int editCount = EDITS.length;
        for (int i = 0; i < editCount; i++) {
            EDITS[i].setTag(i);
            EDITS[i].addTextChangedListener(this);
            EDITS[i].setOnFocusChangeListener(this);
        }
    }


    public String getContent() {
        StringBuffer stringBuffer = new StringBuffer();
        for (EditText edit : EDITS) {
            stringBuffer.append(edit.getText().toString());
        }
        return stringBuffer.toString();
    }


    public KeyboardHandle setKeyBoard(Keyboard board, String[] strings) {
        if (board != null && strings.length != 0) {
            KEYBOARDS.put(board, strings);
            useKeyBoard(board);
        }
        return this;
    }


    public KeyboardHandle setKeyBoard(HashMap<Keyboard, String[]> keyboards) {
        if (!keyboards.isEmpty()) {
            keyboards.putAll(keyboards);
            Iterator iterator = keyboards.entrySet().iterator();
            while (iterator.hasNext()) {
                WeakHashMap.Entry entry = (WeakHashMap.Entry) iterator.next();
                useKeyBoard((Keyboard) entry.getKey());
            }
        }
        return this;
    }

    public void useKeyBoard(Keyboard board) {
        if (board != null) {
            KEYBOARDVIEW.setKeyboard(board);
            String[] strings = KEYBOARDS.get(board);
            if (strings != null && strings.length != 0) {
                mArrString = strings;
            }
        }
    }

    public void showKeyboard() {
        int visibility = KEYBOARDVIEW.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            if (mShowCallback != null) {
                mShowCallback.keyShow();
            }
            KEYBOARDVIEW.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = KEYBOARDVIEW.getVisibility();
        if (visibility == View.VISIBLE) {
            if (mHideCallback != null) {
                mHideCallback.keyHide();
            }
            KEYBOARDVIEW.setVisibility(View.GONE);
        }
    }

    //外部方法结束
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        final EditText focusEdit = EDITS[mFocusEditView];
        if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
            focusEdit.clearFocus();
            hideKeyboard();
        } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
            if (mFocusEditView > 0) {
                if (focusEdit.getText() != null && focusEdit.getText().length() > 0) {
                    focusEdit.setText("");
                } else if (focusEdit.getText() != null && focusEdit.getText().length() == 0) {
                    EDITS[mFocusEditView - 1].requestFocus();
                    if (mDeleteCallback != null) {
                        mDeleteCallback.delete(mFocusEditView + 1);
                    }
                }
            } else {
                focusEdit.setText("");
//                EDITS[mFocusEditView].clearFocus();
//                hideKeyboard();
            }
        } else if (primaryCode > -1) {
            focusEdit.setText(mArrString[primaryCode]);
            focusEdit.setSelection(mArrString[primaryCode].length());
            if (mFocusEditView < EDITS.length - 1) {
                if (EDITS[mFocusEditView + 1].getVisibility() == View.GONE) {
                    if (mEditEndCallback != null) {
                        mEditEndCallback.editEnd();
                    }
                }
                EDITS[mFocusEditView + 1].requestFocus();
            } else if (mFocusEditView == EDITS.length - 1) {
                if (mEditEndCallback != null) {
                    mEditEndCallback.editEnd();
                }
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        final EditText editText = (EditText) view;
        mFocusEditView = (int) editText.getTag();
        editText.setSelection(editText.getText().toString().length());
        if (CALLBACK != null) {
            CALLBACK.startEdit(mFocusEditView);
        }
        hideSoftInputMethod(editText);
        showKeyboard();
    }

    @Override
    public void swipeUp() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }


    @Override
    public void afterTextChanged(Editable editable) {
    }

    public void hideSoftInputMethod(EditText ed) {
        ACTIVITY.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            ed.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName,
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (NoSuchMethodException e) {
                ed.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void setShowCallback(ShowCallback showCallback) {
        this.mShowCallback = showCallback;
    }

    public void setHideCallback(HideCallback hideCallback) {
        this.mHideCallback = hideCallback;
    }

    public void setDeleteCallback(DeleteCallback deleteCallback) {
        this.mDeleteCallback = deleteCallback;
    }

    public void setEditEndCallback(EditEndCallback editEndCallback) {
        this.mEditEndCallback = editEndCallback;
    }

}