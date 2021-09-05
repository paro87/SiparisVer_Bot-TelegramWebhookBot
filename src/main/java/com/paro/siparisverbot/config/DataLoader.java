package com.paro.siparisverbot.config;

import com.paro.siparisverbot.model.Picture;
import com.paro.siparisverbot.model.Product;
import com.paro.siparisverbot.repository.PictureRepository;
import com.paro.siparisverbot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader {
    private final PictureRepository pictureRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DataLoader(PictureRepository pictureRepository, ProductRepository productRepository) {
        this.pictureRepository = pictureRepository;
        this.productRepository = productRepository;
    }

    @PostConstruct
    private void loadData(){
        pictureRepository.saveAll(List.of(
                //A101
                new Picture("001", "katalog_1", "A101", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(2))),
                new Picture("002", "katalog_2", "A101", Date.valueOf(LocalDate.now().plusDays(2)), Date.valueOf(LocalDate.now().plusDays(4))),
                new Picture("003", "katalog_3", "A101", Date.valueOf(LocalDate.now().plusDays(4)), Date.valueOf(LocalDate.now().plusDays(6))),
                new Picture("004", "1", "A101", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(2))),
                new Picture("005", "2", "A101", Date.valueOf(LocalDate.now().plusDays(2)), Date.valueOf(LocalDate.now().plusDays(4))),
                new Picture("006", "3", "A101", Date.valueOf(LocalDate.now().plusDays(4)), Date.valueOf(LocalDate.now().plusDays(6))),

                //BIM
                new Picture("007", "katalog_1", "BIM", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(2))),
                new Picture("008", "katalog_2", "BIM", Date.valueOf(LocalDate.now().plusDays(2)), Date.valueOf(LocalDate.now().plusDays(4))),
                new Picture("009", "katalog_3", "BIM", Date.valueOf(LocalDate.now().plusDays(4)), Date.valueOf(LocalDate.now().plusDays(6))),
                new Picture("010", "1", "BIM", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(2))),
                new Picture("011", "2", "BIM", Date.valueOf(LocalDate.now().plusDays(2)), Date.valueOf(LocalDate.now().plusDays(4))),
                new Picture("012", "3", "BIM", Date.valueOf(LocalDate.now().plusDays(4)), Date.valueOf(LocalDate.now().plusDays(6))),

                //SOK
                new Picture("013", "katalog_1", "SOK", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(2))),
                new Picture("014", "katalog_2", "SOK", Date.valueOf(LocalDate.now().plusDays(2)), Date.valueOf(LocalDate.now().plusDays(4))),
                new Picture("015", "katalog_3", "SOK", Date.valueOf(LocalDate.now().plusDays(4)), Date.valueOf(LocalDate.now().plusDays(6))),
                new Picture("016", "1", "SOK", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(2))),
                new Picture("017", "2", "SOK", Date.valueOf(LocalDate.now().plusDays(2)), Date.valueOf(LocalDate.now().plusDays(4))),
                new Picture("018", "3", "SOK", Date.valueOf(LocalDate.now().plusDays(4)), Date.valueOf(LocalDate.now().plusDays(6)))
                )
        );

        productRepository.saveAll(List.of(
                new Product("ABC123", "kg", 50),
                new Product("DEF456", "pcs.", 10),
                new Product("GHI789", "pcs.", 20),
                new Product("JKL123", "kg", 30),
                new Product("MNO456", "kg", 40)

        ));
    }
}
