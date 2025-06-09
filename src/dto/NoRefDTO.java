package dto;

import model.No;

public record NoRefDTO(
        No no,
        No pai
) {
}
