package id.roogry.cobafileinternal.helper

import android.content.Context
import android.util.Log
import id.roogry.cobafileinternal.model.FileModel

internal object FileHelper {

    fun writeToFile(fileModel: FileModel, context: Context) {
        context.openFileOutput(fileModel.filename, Context.MODE_PRIVATE).use {
            it.write(fileModel.data?.toByteArray())
        }
    }

    fun readFromFile(context: Context, filename: String): FileModel {
        val fileModel = FileModel()
        fileModel.filename = filename
        fileModel.data = context.openFileInput(filename).bufferedReader().useLines { lines ->
            lines.foldIndexed("") { index, content, text ->
                if(index==0) "$text" else "$content\n$text"
            }
        }
        return fileModel
    }
}