package imgui;

import idl.IDLBase;
import idl.helper.IDLString;

/*[-IDL_SKIP]*/
public abstract class ClipboardTextFunction extends IDLBase {

    IDLString TMP = new IDLString((byte)0, (char)0);

    /*[-C++;-NATIVE]
        static jclass clipboardFunctionClass = 0;
        static jmethodID onGetClipboardTextID = 0;
        static jmethodID onSetClipboardTextID = 0;
        class CustomClipboardFunction : public ClipboardTextFunction
        {
        private:
            JNIEnv* env;
            jobject obj;
        public:
            CustomClipboardFunction( JNIEnv* env, jobject obj )
            {
                this->env = env;
                this->obj = obj;
            }
            virtual void onGetClipboardText(std::string* strOut)
            {
                return env->CallVoidMethod(obj, onGetClipboardTextID, (jlong)strOut);
            }
            virtual void onSetClipboardText(std::string* text)
            {
                env->CallVoidMethod(obj, onSetClipboardTextID, (jlong)text);
            }
        };
    */

    /*[-teaVM;-ADD]
        @org.teavm.jso.JSFunctor
        public interface OnGetClipboardText extends org.teavm.jso.JSObject {
            void onGetClipboardTextJS(int strOutAddr);
        }
    */

    /*[-teaVM;-ADD]
        @org.teavm.jso.JSFunctor
        public interface OnSetClipboardText extends org.teavm.jso.JSObject {
            void onSetClipboardTextJS(int textAddr);
        }
    */

    /*[-teaVM;-REPLACE]
        public ClipboardTextFunction() {
            OnGetClipboardText onGetClipboardText = new OnGetClipboardText() {
                @Override
                public void onGetClipboardTextJS (int strOutAddr) {
                    onGetClipboardText(strOutAddr);
                }
            };
            OnSetClipboardText onSetClipboardText = new OnSetClipboardText() {
                @Override
                public void onSetClipboardTextJS (int textAddr) {
                    onSetClipboardText(textAddr);
                }
            };
            int pointer = createNative(onGetClipboardText, onSetClipboardText);
            initNative(pointer, true);
        }
    */
    public ClipboardTextFunction() {
        long addr = createNATIVE();
        initNative(addr, true);
    }

    /*[-C++;-NATIVE]
        if(clipboardFunctionClass == 0) {
            clipboardFunctionClass = (jclass)env->NewGlobalRef(env->GetObjectClass(object));
            onGetClipboardTextID = env->GetMethodID(clipboardFunctionClass, "onGetClipboardText", "(J)V");
            onSetClipboardTextID = env->GetMethodID(clipboardFunctionClass, "onSetClipboardText", "(J)V");
        }
        return (jlong)new CustomClipboardFunction(env, env->NewGlobalRef(object));
    */
    /*[-teaVM;-REPLACE]
        @org.teavm.jso.JSBody(params = { "onGetClipboardText", "onSetClipboardText" }, script = "var jsFunction = new [MODULE].ClipboardTextFunctionImpl(); jsFunction.onGetClipboardText = onGetClipboardText; jsFunction.onSetClipboardText = onSetClipboardText; return [MODULE].getPointer(jsFunction);")
        private static native int createNative(OnGetClipboardText onGetClipboardText, OnSetClipboardText onSetClipboardText);
    */
    private native long createNATIVE();

    private void onGetClipboardText(long strOutAddr) {
        TMP.setCPointer(strOutAddr);
        onGetClipboardText(TMP);
    }

    private void onSetClipboardText(long textAddr) {
        TMP.setCPointer(textAddr);
        onSetClipboardText(TMP);
    }

    public abstract void onGetClipboardText(IDLString strOut);
    public abstract void onSetClipboardText(IDLString text);
}
