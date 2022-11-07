package at.matkollin.service.tebex.config.cache;

import lombok.Getter;
import lombok.SneakyThrows;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Getter
public class CachedBodyServletInputStream extends ServletInputStream {

  private final InputStream cachedBodyInputStream;

  public CachedBodyServletInputStream(byte[] cachedBody) {
    this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
  }

  @SneakyThrows
  @Override
  public boolean isFinished() {
    return this.cachedBodyInputStream.available() == 0;
  }

  @Override
  public boolean isReady() {
    return true;
  }

  @Override
  public void setReadListener(ReadListener readListener) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int read() throws IOException {
    return this.cachedBodyInputStream.read();
  }

}
