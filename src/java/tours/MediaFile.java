package tours;

/**
 Мультимедийный файл (аудио, фото, текст, 3D-модель), привязанный к экспонату.
 Соответствует сущности «Медиафайл» из логической модели.
 */
public class MediaFile {

    private final long mediaId;
    private final String fileUrl;
    private final MediaType mediaType;

    public MediaFile(long mediaId, String fileUrl, MediaType mediaType) {
        this.mediaId = mediaId;
        this.fileUrl = fileUrl;
        this.mediaType = mediaType;
    }

    public long getMediaId() {
        return mediaId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    @Override
    public String toString() {
        return "MediaFile{" + mediaId + ", " + mediaType + ", " + fileUrl + "}";
    }
}
