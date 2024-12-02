package cn.master.tauren.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_refresh_token")
public class RefreshToken implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id(keyType = KeyType.Generator,value = KeyGenerators.flexId)
    private String id;

    /**
     * 是否撤销，0-未撤销，1-已撤销
     */
    private Boolean revoked;

    /**
     * 用户id
     */
    private String userId;

    /**
     * refresh token值
     */
    private String token;
    private long expiryDate;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateDate;

    /**
     * 是否删除，0-未删除，1-已删除
     */
    private Boolean deleted;

}
