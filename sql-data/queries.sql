#QUERIES

#All Students
select *
from student;

#All Trainers
select * 
from trainer;

#All Assignments
select * 
from assignment;

#All courses
select *
from course;

#Students_per_course
select course.title,student.afm,first_name,last_name,date_of_birth,tuition_fees,start_date,end_date
from student join student_course
on student.student_id = student_course.student_id
join course on course.course_id = student_course.course_id


#Trainers_per_course
select course.title,trainer.afm,first_name,last_name
from trainer join trainer_course
on trainer.trainer_id = trainer_course.trainer_id
join course on course.course_id = trainer_course.course_id;

#Assigments_per_course
select course.title,assignment.title
from assignment join assignment_course
on assignment.assignment_id = assignment_course.assignment_id
join course on course.course_id = assignment_course.course_id;


#Assignments_per_student_per_course
select assignment.title,course.title,student.afm,student.first_name,student.last_name
from assignment_student_course join student 
on assignment_student_course.student_id = student.student_id
join assignment on assignment_student_course.assignment_id = assignment.assignment_id
join course on assignment_student_course.course_id = course.course_id;


#students that belong to more than one courses
SELECT afm,first_name,last_name,date_of_birth FROM student 
join student_course on 
student.student_id = student_course.student_id group by student.student_id HAVING COUNT(*) > 1;