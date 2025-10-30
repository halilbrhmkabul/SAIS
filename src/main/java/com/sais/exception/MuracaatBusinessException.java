package com.sais.exception;

/**
 * Müracaat işlemleri için özel business exception
 */
public class MuracaatBusinessException extends BusinessException {
    
    public MuracaatBusinessException(String message) {
        super(message);
    }
    
    public MuracaatBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public static MuracaatBusinessException bekleyenMuracaatVar() {
        return new MuracaatBusinessException("Bu kişinin daha önce müracaatı vardır");
    }
    
    public static MuracaatBusinessException sadeceBekleyenIptalEdilebilir() {
        return new MuracaatBusinessException("Sadece beklemede olan müracaatlar iptal edilebilir");
    }
    
    public static MuracaatBusinessException tahkikataSevkGerekli() {
        return new MuracaatBusinessException("Tutanak için müracaat durumu 'Tahkikata Sevk' olmalıdır");
    }
}
