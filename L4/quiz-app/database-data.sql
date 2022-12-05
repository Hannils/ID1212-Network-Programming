INSERT INTO public.users (username, password)
VALUES  ('kalle', 'admin'), 
        ('hampus', 'admin');

INSERT INTO public.questions (text, options, answer)
VALUES
    ('In golf, what name is given to a hole score of two under par?', ARRAY ['Birdie','Bogey','Albatross'], 'Eagle'),
    ('Who wrote the award winning musical &quot;In The Heights&quot;?', ARRAY ['Steven Sondheim','Francis Scott Key','John Phillips Sousa'], 'Lin-Manuel Miranda'),
    ('What prime number comes next after 19?', ARRAY ['25','21','27'], '23'),
    ('Which driver has been the Formula 1 world champion for a record 7 times?', ARRAY ['Ayrton Senna','Fernando Alonso','Jim Clark'], 'Michael Schumacher'),
    ('What was the subject of the 2014 song &quot;CoCo&quot; by American rapper O. T. Genasis?', ARRAY ['Conan O&#039;Brien','Cobalt(II) carbonate','Coconut cream pie'], 'Cocaine'),
    ('What company develops the Rock Band series of rhythm games?', ARRAY ['Activision','Konami','Electronic Arts'], 'Harmonix'),
    ('71% of the Earth&#039;s surface is made up of', ARRAY ['Deserts','Continents','Forests'], 'Water'),
    ('Which city did the former NHL team &quot;The Nordiques&quot; originiate from?', ARRAY ['Houston','Montreal','New York'], 'Quebec City'),
    ('Who created the &quot;Metal Gear&quot; Series?', ARRAY ['Hiroshi Yamauchi','Shigeru Miyamoto','Gunpei Yokoi'], 'Hideo Kojima'),
    ('When was the Playstation 3 released?', ARRAY ['January 8, 2007','December 25, 2007','July 16, 2006'], 'November 11, 2006');

INSERT INTO public.quizzes (subject)
VALUES ('category 1'), ('category 2');

INSERT INTO selector(quiz_id, question_id)
VALUES 
((SELECT id from public.quizzes WHERE subject='category 1'), (SELECT id from public.questions WHERE text='In golf, what name is given to a hole score of two under par?')),
((SELECT id from public.quizzes WHERE subject='category 1'), (SELECT id from public.questions WHERE text='Who wrote the award winning musical &quot;In The Heights&quot;?')),
((SELECT id from public.quizzes WHERE subject='category 1'), (SELECT id from public.questions WHERE text='What prime number comes next after 19?')),
((SELECT id from public.quizzes WHERE subject='category 1'), (SELECT id from public.questions WHERE text='Which driver has been the Formula 1 world champion for a record 7 times?')),
((SELECT id from public.quizzes WHERE subject='category 1'), (SELECT id from public.questions WHERE text='What was the subject of the 2014 song &quot;CoCo&quot; by American rapper O. T. Genasis?')),
((SELECT id from public.quizzes WHERE subject='category 2'), (SELECT id from public.questions WHERE text='What company develops the Rock Band series of rhythm games?')),
((SELECT id from public.quizzes WHERE subject='category 2'), (SELECT id from public.questions WHERE text='71% of the Earth&#039;s surface is made up of')),
((SELECT id from public.quizzes WHERE subject='category 2'), (SELECT id from public.questions WHERE text='Which city did the former NHL team &quot;The Nordiques&quot; originiate from?')),
((SELECT id from public.quizzes WHERE subject='category 2'), (SELECT id from public.questions WHERE text='Who created the &quot;Metal Gear&quot; Series?')),
((SELECT id from public.quizzes WHERE subject='category 2'), (SELECT id from public.questions WHERE text='When was the Playstation 3 released?')),