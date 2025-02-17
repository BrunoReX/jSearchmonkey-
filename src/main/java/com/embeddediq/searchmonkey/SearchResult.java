/*
 * Copyright (C) 2017-18 cottr
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.embeddediq.searchmonkey;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author cottr
 */
public class SearchResult {
    public SearchResult(Path file, BasicFileAttributes attrs, long matchCount, String mimeType)
    {
        fileName = FilenameUtils.getName(file.toString());
        fileExtension = FilenameUtils.getExtension(fileName);
        pathName = FilenameUtils.getFullPath(file.toString());
        fileSize = attrs.size();
        lastModified = attrs.lastModifiedTime();
        creationTime = attrs.creationTime();
        lastAccessTime = attrs.lastAccessTime();
        fileFlags = 0;
        if (attrs.isSymbolicLink()) fileFlags |= SYMBOLIC_LINK;
        try {
            if (Files.isHidden(file)) fileFlags |= HIDDEN_FILE;
            contentType = mimeType != null ? mimeType : Files.probeContentType(file);
        } catch (IOException ex) {
            Logger.getLogger(SearchResult.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.matchCount = matchCount;
        fileIcon = GetIconIndex();
    }

    public SearchResult(Path file, BasicFileAttributes attrs, String mimeType)
    {
        this(file, attrs, 0, mimeType);
    }
    
    public Object get(int column)
    {
        switch (column)
        {
            case FILENAME:
                return fileName;
            case EXTENSION:
                return fileExtension;
            case FOLDER:
                return pathName;
            case SIZE:
                return fileSize;
            case MODIFIED:
                return lastModified;
            case ACCESSED:
                return lastAccessTime;
            case CREATED:
                return creationTime;
            case FLAGS:
                return fileFlags;
            case COUNT:
                return matchCount;
            case CONTENT_TYPE:
                return contentType;
            case FILE_ICON:
                return fileIcon;
            default:
                break;
        }
        throw new IllegalArgumentException("Column ID out of bounds");
    }

    public String fileName;
    public String fileExtension;
    public String pathName;
    public long fileSize;
    public FileTime lastModified;
    public FileTime lastAccessTime;
    public FileTime creationTime;
    public int fileFlags;
    public long matchCount;
    public String contentType;
    public int fileIcon; // Icon index nr

    // Flags
    public final static int SYMBOLIC_LINK = 0x1;
    public final static int HIDDEN_FILE = 0x2;
    // public final int HIDDEN_FILE = 0x2;

    /**
     *
     */
    public final static String[] COLUMN_NAMES = new String[] {
        "SearchResult.Icon.String", // 0 = Icon
        "SearchResult.File.String", // 1
        "SearchResult.Folder.String", // 2
        "SearchResult.FileSize.String", // 3
        "SearchResult.Count.String", // 4
        "SearchResult.LastModified.String", // 5
        "SearchResult.Created.String", // 6
        "SearchResult.LastAccessed.String", // 7
        "SearchResult.Flags.String", // 8
        "SearchResult.Extension.String", // 9
        "SearchResult.ContentType.String", // 10
    };

    public final static Class[] COLUMN_CLASSES = new Class[]  {
        Integer.class, // Icon
        String.class,
        String.class,
        Long.class,
        Long.class,
        FileTime.class,
        FileTime.class,
        FileTime.class,
        Integer.class,
        String.class,
        String.class,
    };

    public final static int[] COLUMN_WIDTH = new int[]  {
        35, /* File icon */
        200, /* Filename */
        400, /* Folder */
        100, /* File Size */
        50, /* Count */
        100, /* Modified */
        100, /* Created */
        100, /* Accessed */
        50, /* Flags */
        80, /* Extension */
        200, /* Content Type */
    };

    // Forced enumeration of the column names
    public final static int FILE_ICON = 0; // ICON
    public final static int FILENAME = 1;
    public final static int FOLDER = 2;
    public final static int SIZE = 3;
    public final static int COUNT = 4;
    public final static int MODIFIED = 5;
    public final static int CREATED = 6;
    public final static int ACCESSED = 7;
    public final static int FLAGS = 8;
    public final static int EXTENSION = 9;
    public final static int CONTENT_TYPE = 10;
    
    public Object[] toArray()
    {
        Object[] def = new Object[] {
            this.fileIcon,
            this.fileName,
            this.pathName,
            this.fileSize,
            this.matchCount,
            this.lastModified,
            this.creationTime,
            this.lastAccessTime,
            this.fileFlags,
            this.fileExtension,
            this.contentType,
        };
        return def;
    }
    
    private int GetIconIndex()
    {
        if (contentType == null) return ICON_UNKNOWN;
        
        if (this.contentType.startsWith("application/"))
        {
            if (contentType.startsWith("application/zip") ||
                    contentType.startsWith("application/x-zip") ||
                    contentType.startsWith("application/x-7z"))
                return ICON_ARCHIVE;
            else if (contentType.equals("application/postscript") ||
                    contentType.equals("application/pdf"))
                return ICON_PDF;
            else if (contentType.equals("application/java-archive"))
                return ICON_JAVA;
            else if (contentType.equals("application/xml"))
                return ICON_HTM;
            else if (contentType.equals("application/x-font"))
                return ICON_FONT;
            else if (contentType.equals("application/vnd.oasis.opendocument.database"))
                return ICON_ODB;
            else if (contentType.equals("application/vnd.oasis.opendocument.graphics"))
                return ICON_ODG;
            else if (contentType.equals("application/vnd.oasis.opendocument.presentation"))
                return ICON_ODP;
            else if (contentType.equals("application/vnd.oasis.opendocument.spreadsheet"))
                return ICON_ODS;
            else if (contentType.equals("application/vnd.oasis.opendocument.text"))
                return ICON_ODT;
            else if (contentType.equals("application/vnd.ms-excel") ||
                    contentType.startsWith("application/vnd.openxmlformats-officedocument.spreadsheetml"))
                return ICON_XLS;
            else if (contentType.equals("application/ms-word") || contentType.equals("application/msword") || 
                    contentType.equals("application/vnd.ms-word") || contentType.equals("application/vnd.msword") ||
                    contentType.startsWith("application/vnd.openxmlformats-officedocument.wordprocessingml"))
                return ICON_DOC;
            else if (contentType.equals("application/vnd.ms-powerpoint") ||
                    contentType.equals("application/vnd.ms-powerpoint.presentation") ||
                    contentType.startsWith("application/vnd.openxmlformats-officedocument.presentationml"))
                return ICON_PPT;
            else if (contentType.equals("application/x-abiword"))
                return ICON_WORDPROC;
            
        }
        else if (this.contentType.startsWith("audio/"))
        {
            return ICON_AUDIO;            
        }
        else if (this.contentType.startsWith("chemical/"))
        {
            return ICON_TEXT;
        }
        else if (this.contentType.startsWith("image/"))
        {
            return ICON_IMAGE;
        }
        else if (this.contentType.startsWith("text/"))
        {
            if (contentType.equals("text/x-java")) {
                return ICON_JAVA;
            } else if (contentType.equals("text/html") || contentType.equals("text/css")) {
                return ICON_HTM;
            } else if (contentType.equals("text/csv")) {
                return ICON_SPREADSHEET;
            } else if (contentType.startsWith("text/x-c"))
            {
                if (contentType.equals("text/x-chdr")) {
                    return ICON_H;
                }
                return ICON_C;
            }
            return ICON_TEXT;            
        }
        else if (this.contentType.startsWith("video/"))
        {
            return ICON_VIDEO;
        }

        return ICON_UNKNOWN;
    }

    private static int ICON_ARCHIVE = 0;
    private static int ICON_AUDIO = 1;
    private static int ICON_C = 2;
    private static int ICON_DOC = 3;
    private static int ICON_DRAWING = 4;
    private static int ICON_FONT = 5;
    private static int ICON_H = 6;
    private static int ICON_HTM = 7;
    private static int ICON_IMAGE = 8;
    private static int ICON_JAVA = 9;
    private static int ICON_ODB = 10;
    private static int ICON_ODG = 11;
    private static int ICON_ODP = 12;
    private static int ICON_ODS = 13;
    private static int ICON_ODT = 14;
    private static int ICON_PDF = 15;
    private static int ICON_PPT = 16;
    private static int ICON_SPREADSHEET = 17;
    private static int ICON_TEXT = 18;
    private static int ICON_UNKNOWN = 19;
    private static int ICON_VIDEO = 20;
    private static int ICON_WORDPROC = 21;
    private static int ICON_XLS = 22;
    

}

