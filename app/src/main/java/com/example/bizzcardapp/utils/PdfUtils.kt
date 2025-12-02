package com.example.bizzcardapp.utils

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.example.bizzcardapp.model.PortfolioItem
import com.example.bizzcardapp.model.UserProfile

import java.io.File
import java.io.FileOutputStream

fun generatePdfAndSave(
    context: Context,
    profile: UserProfile,
    projects: List<PortfolioItem>
): File? {
    return try {
        val doc = PdfDocument()
        val pageWidth = 595
        val pageHeight = 842
        var y = 40f
        val paint = Paint()

        // Profile page
        val profilePage = doc.startPage(
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        )

        val canvas = profilePage.canvas
        paint.textSize = 18f
        canvas.drawText(profile.name, 24f, y, paint)
        y += 24f

        paint.textSize = 14f
        canvas.drawText(profile.title, 24f, y, paint)
        y += 20f

        canvas.drawText(profile.email, 24f, y, paint)
        y += 20f

        profile.profileImageUri?.let {
            val bmp = loadBitmapFromUri(context, Uri.parse(it))
            bmp?.let { b ->
                val scaled = scaleBitmapToWidth(b, 120)
                canvas.drawBitmap(scaled, pageWidth - scaled.width - 24f, 40f, paint)
            }
        }

        doc.finishPage(profilePage)

        // Projects
        var page = doc.startPage(
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 2).create()
        )
        var c = page.canvas
        y = 40f

        for (proj in projects) {
            if (y > pageHeight - 100) {
                doc.finishPage(page)
                page = doc.startPage(
                    PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 3).create()
                )
                c = page.canvas
                y = 40f
            }

            paint.textSize = 16f
            c.drawText(proj.title, 24f, y, paint)
            y += 20f

            paint.textSize = 12f
            c.drawText(proj.description, 24f, y, paint)
            y += 20f

            proj.imageUri?.let {
                val bmp = loadBitmapFromUri(context, it.toUri())
                bmp?.let { b ->
                    val scaled = scaleBitmapToWidth(b, 160)
                    c.drawBitmap(scaled, 24f, y, paint)
                    y += scaled.height + 20f
                }
            }
        }

        doc.finishPage(page)

        val file = File(context.cacheDir, "portfolio_${System.currentTimeMillis()}.pdf")
        doc.writeTo(FileOutputStream(file))
        doc.close()
        file

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun sharePdf(context: Context, file: File) {
    try {
        val uri = FileProvider.getUriForFile(
            context,
            context.packageName + ".fileprovider",
            file
        )
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Share PDF"))
    } catch (e: Exception) {
        Toast.makeText(context, "Unable to share PDF", Toast.LENGTH_SHORT).show()
    }
}
