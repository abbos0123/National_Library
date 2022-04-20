package uz.library.national_library.models;

import io.swagger.annotations.ApiModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@ApiModel
public enum BookCategory {
    MATEMATIKA,
    DASTURLASH,
    FIZIKA,
    KIMYO,
    BIOLOGIYA,
    IJTIMOIY,
    TARIX,
    GEOGRAFIYA,
    GEOLOGIYA,
    XORIJIY_TILLAR,
    EKALOGIYA,
    GIDROMETROLOGIYA,
    SPORT,
    IQTISODIYOT
}
