package cleanie.repatch.post.model.request;

import java.util.List;
import java.util.Set;

public record PostRequest(
        String postType,
        String fabricType,
        String title,
        String unit,
        String price,
        String content,
        Set<String> transactionTypes,
        List<Long> photoIds
) {}
