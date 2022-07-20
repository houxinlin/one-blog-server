package com.hxl.blog.controller.ip.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author hxl
 * @since 2021-11-12
 */
@TableName("tb_ip")
public class TbIp implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String ipAddress;

    private String ipCity;

    private String ipProvince;

    private LocalDateTime createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public String getIpCity() {
        return ipCity;
    }

    public void setIpCity(String ipCity) {
        this.ipCity = ipCity;
    }
    public String getIpProvince() {
        return ipProvince;
    }

    public void setIpProvince(String ipProvince) {
        this.ipProvince = ipProvince;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "TbIp{" +
            "id=" + id +
            ", ipAddress=" + ipAddress +
            ", ipCity=" + ipCity +
            ", ipProvince=" + ipProvince +
            ", createDate=" + createDate +
        "}";
    }
}
