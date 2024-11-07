package cleanie.repatch.post.service;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.post.model.PostRequest;
import org.springframework.stereotype.Component;

@Component
public class PostValidator {

    public void validatePostRequest(PostRequest request){
        if (!validatePostPublishRequest(request)){
            throw new BadRequestException(ExceptionCode.INVALID_REQUEST);
        }
    }

    public boolean validatePostPublishRequest(PostRequest request) {
        return !request.postType().isBlank() && !request.fabricType().isBlank() &&
                !request.title().isBlank() && !request.unit().isBlank() &&
                !request.price().isBlank() && !request.content().isBlank() &&
                !request.transactionTypes().isEmpty();
    }
}
