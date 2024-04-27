select now();

insert into place_area(id, created_date, latitude, longitude, name, region_id, status)
values (1, now(), 40.658320, 72.228755, '1-qator 1-ustun', 1, 'AVAILABLE'),
       (2, now(), 40.658307, 72.228772, '1-qator 2-ustun', 1, 'AVAILABLE'),
       (3, now(), 40.658297, 72.228795, '1-qator 3-ustun', 1, 'AVAILABLE'),
       (4, now(), 40.658280, 72.228817, '1-qator 4-ustun', 1, 'AVAILABLE'),
       (5, now(), 40.658356, 72.228789, '1-qator 5-ustun', 1, 'AVAILABLE'),
       (6, now(), 40.658443, 72.228842, '3-qator 1-ustun', 1, 'AVAILABLE'),
       (7, now(), 40.658432, 72.228855, '3-qator 2-ustun', 1, 'AVAILABLE'),
       (8, now(), 40.658458, 72.228898, '4-qator 2-ustun', 1, 'AVAILABLE'),
       (9, now(), 40.658531, 72.228950, '5-qator 1-ustun', 1, 'AVAILABLE'),
       (10, now(), 40.658526, 72.228978, '5-qator 2-ustun', 1, 'AVAILABLE'),
       (11, now(), 40.658514, 72.229000, '5-qator 3-ustun', 1, 'AVAILABLE')
on conflict (id) do nothing;


insert into car(id, color, created_date, image_url, model, number, place_id, position, recipient_name,
                recipient_region_id, status, type)
values (1, 'Qora', now(),
        'https://fergana.agency/siteapi/media/images/b502e6a6-1db1-4f80-a9ab-a51b703a42c2.png', 'Gentra',
        'XWBSA695ERA070217', null, '3-pozitsiya', 'Brain savdo', 1, 'NOT_LOCATED', 'sedan'),
       (2, 'Qora', now(),
        'https://fergana.agency/siteapi/media/images/b502e6a6-1db1-4f80-a9ab-a51b703a42c2.png', 'Gentra',
        'XWBSA695ERA070218', null, '1-pozitsiya', 'Brain savdo', 1, 'NOT_LOCATED', 'sedan'),
       (3, 'Qora', now(),
        'https://fergana.agency/siteapi/media/images/b502e6a6-1db1-4f80-a9ab-a51b703a42c2.png', 'Gentra',
        'XWBSA695ERA070219', null, '2-pozitsiya', 'Brain savdo', 1, 'NOT_LOCATED', 'sedan'),
       (4, 'Ko''k', now(),
        'https://www.afisha.uz/uploads/media/2019/11/0789008_m.png', 'Malibu',
        'XWBSA695ERA070220', null, '4-pozitsiya', 'Asl', 1, 'NOT_LOCATED', 'sedan'),
       (5, 'Ko''k', now(),
        'https://www.afisha.uz/uploads/media/2019/11/0789008_m.png', 'Malibu',
        'XWBSA695ERA070221', null, '2-pozitsiya', 'Asl', 1, 'NOT_LOCATED', 'sedan'),
       (6, 'Ko''k', now(),
        'https://www.afisha.uz/uploads/media/2019/11/0789008_m.png', 'Malibu',
        'XWBSA695ERA070222', null, '3-pozitsiya', 'Asl', 1, 'NOT_LOCATED', 'sedan'),
       (7, 'Ko''k', now(),
        'https://www.afisha.uz/uploads/media/2019/11/0789008_m.png', 'Malibu',
        'XWBSA695ERA070223', null, '3-pozitsiya', 'Asl', 1, 'NOT_LOCATED', 'sedan'),
       (8, 'Ko''k', now(),
        'https://www.afisha.uz/uploads/media/2019/11/0789008_m.png', 'Malibu',
        'XWBSA695ERA070224', null, '3-pozitsiya', 'Asl', 1, 'NOT_LOCATED', 'sedan')
on conflict (id) do nothing;